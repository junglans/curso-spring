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

  private clients : Array<Client>;

  constructor(private clientService: ClientService) {}

  ngOnInit() {
    console.log("ngOnInit");
    this.getClientList();
  }

  public delete(id: number): void {
  
    Swal.fire({ title: 'Está Ud. seguro?',
                text: "La acción no podrá ser revertida!",
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'OK'
    }).then((result) => {
      if (result.value) {
          this.clientService.delete(id).subscribe ( (response) => {
              this.getClientList();
              Swal.fire('Baja de cliente', `Cliente ${response.name} eliminado con éxito`, 'success');
          }, (err) => this.showErrorMessage(err));
      }
    })
  }

  private getClientList() {
    this.clientService.getAllClients().subscribe( (response: Array<Client>) => {
      this.clients = response;
    }, (err) => this.showErrorMessage(err));
  }

  private showErrorMessage(err: any) {
    Swal.fire({
      type: 'error',
      title: 'Se ha producido un error',
      text: JSON.stringify(err.message)
    })
  }
}
