package com.curso.springboot.jpa.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.springboot.jpa.models.bean.ClientBean;
import com.curso.springboot.jpa.models.dto.ClientDTO;
import com.curso.springboot.jpa.services.ClientService;
import com.curso.springboot.jpa.utils.MapperUtil;
import com.curso.springboot.jpa.utils.paginator.PageRenderer;

@Controller
@SessionAttributes("client") // Se guarda el objeto cliente en la sesión.
public class ClientController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;
	@Autowired
	private MapperUtil mapper;

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/uploads/{filename:.+}",  method=RequestMethod.GET)
	public ResponseEntity<Resource> getPhoto(@PathVariable(value="filename") String filename) {
		
		Path pathPhoto = Paths.get("uploads").resolve(filename).toAbsolutePath();
		Resource resource = null;
		try {
			
			resource = new UrlResource(pathPhoto.toUri());
			if (!resource.exists() && !resource.isReadable()) {
				throw new RuntimeException("La imagen no exite o no es accesible.");
			}
			
		} catch (MalformedURLException e) {
			LOGGER.error("La imagen no exite o no es accesible.",e);
		}

		return (ResponseEntity<Resource>) ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attacment; filename=\"" + resource.getFilename() + "\"");
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		ClientBean client = mapper.map(clientService.findById(id), ClientBean.class);
		if (client == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "clients";
		}
		model.addAttribute("title", "Detalle del cliente " + client.getName() + " " + client.getSurname());
		model.addAttribute("client", client);
		return "detail";
	}

	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = new PageRequest(page, 5);
		Page<ClientDTO> dtoPage = clientService.findAll(pageRequest);

		List<ClientBean> beanList = mapper.map(dtoPage.getContent(), ClientBean.class);
		Page<ClientBean> beanPage = new PageImpl<ClientBean>(beanList, pageRequest, dtoPage.getTotalElements());
		PageRenderer<ClientBean> pageRenderer = new PageRenderer<ClientBean>("/clients", beanPage);
		model.addAttribute("title", "Listado de Clientes");
		model.addAttribute("clientList", beanPage);
		model.addAttribute("page", pageRenderer);

		return "clients";

	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("title", "Formulario de Cliente");
		model.addAttribute("client", new ClientBean());
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@ModelAttribute("client") @Valid ClientBean client, // el model atribute se toma de la sesión.
			@RequestParam("file") MultipartFile photo, BindingResult bindingResult, Model model, SessionStatus status,
			RedirectAttributes flash) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("title", "Formulario de Cliente");
			return "form";
		}

		if (!photo.isEmpty()) {
			// con esto se almacena la imagen como un recurso dentro del proyecto.
			// Path uploadDirectory = Paths.get("src//main//resources//static//uploads");
			// String uploadPath = uploadDirectory.toFile().getAbsolutePath();

			try {

				String uniquefileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();

				Path rootPath = Paths.get("uploads").resolve(uniquefileName);
				Path rootAbsolutePath = rootPath.toAbsolutePath();

				Files.copy(photo.getInputStream(), rootAbsolutePath);

				flash.addFlashAttribute("info",
						"El archivo " + photo.getOriginalFilename() + " ha sido guardado correctamente");
				client.setPhoto(uniquefileName);

			} catch (IOException e) {
				LOGGER.error("Error almacenando la imagen del cliente.", e);
			}
		}

		clientService.save(mapper.map(client, ClientDTO.class));
		status.setComplete(); // Se elimina el objeto cliente de la sesión.
		flash.addFlashAttribute("success", "Cliente creado con éxito.");
		return "redirect:/clients";
	}

	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id != null) {
			clientService.delete(id);
		}
		flash.addFlashAttribute("success", "Cliente eliminado con éxito.");
		return "redirect:/clients";
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String findById(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		ClientBean client = null;
		if (id != null) {

			client = mapper.map(clientService.findById(id), ClientBean.class);
			if (client != null) {
				model.addAttribute("title", "Formulario de Cliente");
				model.addAttribute("client", client);
				return "form";
			} else {
				flash.addFlashAttribute("error", "No existe un cliente con el identificador:" + id);
				return "redirect:/clients";
			}

		} else {
			flash.addFlashAttribute("error", "El identificador del cliente no puede ser nulo.");
			return "redirect:/clients";
		}

	}
}
