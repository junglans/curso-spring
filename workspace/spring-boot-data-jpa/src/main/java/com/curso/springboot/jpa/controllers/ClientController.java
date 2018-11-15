package com.curso.springboot.jpa.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import com.curso.springboot.jpa.services.ClientServiceImpl;
import com.curso.springboot.jpa.services.IUploadFileService;
import com.curso.springboot.jpa.utils.GlobalValues;
import com.curso.springboot.jpa.utils.MapperUtil;
import com.curso.springboot.jpa.utils.paginator.PageRenderer;

@Controller
@SessionAttributes("client") // Se guarda el objeto cliente en la sesión.
public class ClientController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ClientServiceImpl clientService;
	@Autowired
	private MapperUtil mapper;
	@Autowired
	private IUploadFileService uploadFileService;

	@RequestMapping(value = "/uploads/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<Resource> getPhoto(@PathVariable(value = "filename") String filename) throws Exception {

		try {
			Resource resource = uploadFileService.load(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}

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
			RedirectAttributes flash) throws IOException {

		if (bindingResult.hasErrors()) {
			model.addAttribute("title", "Formulario de Cliente");
			return "form";
		}

		if (!photo.isEmpty()) {

			if (client.getId() != null) {

				String photoName = client.getPhoto();
				if (photoName != null) {
					Path photoPath = Paths.get(GlobalValues.UPLOADFOLDER).resolve(client.getPhoto()).toAbsolutePath();
					File file = photoPath.toFile();
					if (file.exists() && file.canRead()) {
						file.delete();
					}
				}
			}

			try {
				String uniquefileName = uploadFileService.copy(photo);
				flash.addFlashAttribute("info",
						"El archivo " + photo.getOriginalFilename() + " ha sido guardado correctamente");
				client.setPhoto(uniquefileName);

			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
				throw e;
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
			ClientBean client = mapper.map(clientService.findById(id), ClientBean.class);
			if (client != null) {
				clientService.delete(id);
				String photoName = client.getPhoto();
				if (photoName != null) {
					if (uploadFileService.delete(photoName)) {
						flash.addFlashAttribute("success", "Fotografía eliminada con exito");
					} else {
						flash.addFlashAttribute("error", "Fotografía no pudo eliminarse");
					}

				}
			}
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
