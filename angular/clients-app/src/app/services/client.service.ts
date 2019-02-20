import { Injectable } from '@angular/core';
import { Client } from '../clients/client';
import { of, Observable } from 'rxjs';
 
@Injectable()
 
export class ClientService {

  constructor() { }

  public getAllClients(): Observable<Array<Client>> {

      let clients = new Array<Client>();
     
      clients.push(new Client(1, 'Juan Francisco', 'Jiménez Pérez','19/02/2019','jf.jimenez@mnemo.com'));
      clients.push(new Client(2, 'Eva María', 'Roldán Martín','19/02/2019','eva.roldan@mnemo.com'));
      clients.push(new Client(3, 'Pedro', 'Lopés','19/02/2019','pelo@mnemo.com'));
      clients.push(new Client(4, 'Stephen', 'Hawking','20/02/2019','s.hawk@mnemo.com'));
      clients.push(new Client(5, 'Hector', 'Socas Navarro','20/02/2019','h.socas.navarro@mnemo.com'));
      clients.push(new Client(6, 'Francis', 'Montesinos','20/02/2019','emulenews@mnemo.com'));

      return of(clients);
  }
}
