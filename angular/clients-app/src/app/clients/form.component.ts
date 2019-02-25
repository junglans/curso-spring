import { Component, OnInit } from '@angular/core';
import { Client } from './client';
import { ClientService } from '../services/client.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styles: []
})
export class FormComponent implements OnInit {

  private client:Client = new Client(null, null, null, null, null);
  private title:string = "Nuevo Cliente"
  constructor(private router: Router, private clientService: ClientService) { }

  ngOnInit() {
  }

  public create():void {
     this.clientService.create(this.client).subscribe(
       response =>{  
         this.router.navigate(['/clients']);
         Swal.fire('Nuevo Cliente', `Cliente ${this.client.name} creado con éxito`, 'success');
        },
       err => {
         return console.log(JSON.stringify(err));
       }
       );
  }
}
