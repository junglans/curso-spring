import { Component, OnInit } from '@angular/core';
import { Client } from './client';
import { ClientService } from '../services/client.service';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styles: []
})
export class FormComponent implements OnInit {

  private client:Client = new Client(null, null, null, null, null);
  private title:string = "Cliente";
  private action = "";
  constructor(private router: Router, private activatedRoute: ActivatedRoute, private clientService: ClientService) { }

  ngOnInit() {
    this.get();
  }

  public get(): void {
     this.activatedRoute.params.subscribe( params => {
          if (params['id']) {
             this.clientService.get(params['id']).subscribe( client => this.client = client);
             this.action = "Actualizar";
          } else {
             this.action = "Crear";
          }
     });
  }

  public doAction() : void {
    if ('Crear' == this.action) {
      this.create();
    } else {
      this.update();
    }
  }
  public create(): void {
     this.clientService.create(this.client).subscribe(
          response =>{  
            this.router.navigate(['/clients']);
            Swal.fire(this.title, `Cliente ${this.client.name} creado con éxito`, 'success');
            },
          err => {
            return console.log(JSON.stringify(err));
          }
       );
  }

  public update(): void {
    this.clientService.update(this.client).subscribe(
        response =>{  
          this.router.navigate(['/clients']);
          Swal.fire(this.title, `Cliente ${this.client.name} actualizado con éxito`, 'success');
        },
        err => {
          return console.log(JSON.stringify(err));
        }
      );
  }
}
