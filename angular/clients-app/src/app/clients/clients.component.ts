import { Component, OnInit } from '@angular/core';
import { Client } from './client';
import { ClientService } from '../services/client.service';
@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styles: []
})
export class ClientsComponent implements OnInit {

  private clients:Array<Client>;

  constructor(private clientService: ClientService) {}

  ngOnInit() {
    this.clientService.getAllClients().subscribe( (response: Array<Client>) => {
      //console.log(JSON.stringify(response));
      this.clients = response;
    });
  }

}
