import { Injectable } from '@angular/core';
import { Client } from '../clients/client';
import { Observable } from 'rxjs';
import { BaseService } from "./base.service";
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class ClientService   {

  private urlListClient:string = "http://localhost:8080/api/client/list";
  private urlClient:string = "http://localhost:8080/api/client";
   
  constructor(private service: BaseService) {}

  public getAllClients(): Observable<Array<Client>> {
   
    return this.service.executeRequest<Array<Client>>("GET", this.urlListClient);
    /*
    return this.http.request(new HttpRequest("GET", this.url)).pipe(
      map (  ( response : HttpResponse<Array<Client>> ) => {
        return response.body as Array<Client>;
      } ));
      
      return this.http.get (this.url).pipe( map ( (response) => response as (Array<Client>) ) );
      return this.http.get<Array<Client>>(this.url);
    
      let clients = new Array<Client>();
     
      clients.push(new Client(1, 'Juan Francisco', 'Jiménez Pérez','19/02/2019','jf.jimenez@mnemo.com'));
      clients.push(new Client(2, 'Eva María', 'Roldán Martín','19/02/2019','eva.roldan@mnemo.com'));
      clients.push(new Client(3, 'Pedro', 'Lopés','19/02/2019','pelo@mnemo.com'));
      clients.push(new Client(4, 'Stephen', 'Hawking','20/02/2019','s.hawk@mnemo.com'));
      clients.push(new Client(5, 'Hector', 'Socas Navarro','20/02/2019','h.socas.navarro@mnemo.com'));
      clients.push(new Client(6, 'Francis', 'Montesinos','20/02/2019','emulenews@mnemo.com'));

      return of(clients);
      */
  }

  public create(client: Client): Observable<Client> {
    return this.service.executeRequest<Client>("POST", this.urlClient, client, {headers: new HttpHeaders({'Content-Type':'application/json'})});
  }

  public get(id: number): Observable<Client> {
    return this.service.executeRequest<Client>("GET",`${this.urlClient}/${id}`);
  }

  public update(client: Client): Observable<Client> {
    return this.service.executeRequest<Client>("PUT", `${this.urlClient}/${client.id}`, client, {headers: new HttpHeaders({'Content-Type':'application/json'})});
  }

  public delete(id: number): Observable<any> {
     return this.service.executeRequest<any>("DELETE", `${this.urlClient}/${id}`);
  }
}
