import { Component, OnInit } from '@angular/core';
import { Client } from './client';
import { ClientService } from '../services/client.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styles: []
})
export class ClientsComponent implements OnInit {

  private clients:Array<Client>;

  constructor(private clientService: ClientService) {}

  ngOnInit() {
    this.getClientList();
  }

  public delete(id: number): void {
  
    Swal.fire({
      title: 'Está Ud. seguro?',
      text: "La acción no podrá ser revertida!",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'OK'
    }).then((result) => {
      if (result.value) {
        this.clientService.delete(id).subscribe ( () => {

          this.getClientList();
          Swal.fire('Baja de cliente', 'Cliente eliminado con éxito', 'success');
  
      }, 
        (err) => console.log("Se ha producido un error :" + JSON.stringify(err.message))
      );
      }
    })

  }

  private getClientList() {
    this.clientService.getAllClients().subscribe( (response: Array<Client>) => {
      this.clients = response;
    }, (err) => console.log("Se ha producido un error :" + JSON.stringify(err.message)));
  }
}
