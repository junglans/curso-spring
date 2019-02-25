import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { filter } from 'rxjs/internal/operators/filter';
import { map } from 'rxjs/internal/operators/map';
import { catchError  } from 'rxjs/internal/operators/catchError';
import { throwError } from 'rxjs';

@Injectable()
export class BaseService {

  constructor(private http: HttpClient) {}

  public executeRequest<T>(method: string, url: string, body?: any, init?: any):  Observable<T> {
    return this.http.request(new HttpRequest(method, url, body, init)).pipe(
        filter( (response) => response instanceof HttpResponse),
        map((response: HttpResponse<T>) => {
            return response.body as T;
        }),
        catchError(this.handleError)
    );
  }
  /**
   *
   * @param error En este método podemos controlar el error y realizar modificaciones
   * Por ejemplo podemos modificar el mensaje.
   */
  private handleError(error: HttpErrorResponse) {
     
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // error.error.errors.message = 'Ups! Se ha producido un error';
    return throwError(error);
  }
}