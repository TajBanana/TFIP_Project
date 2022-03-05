import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {LoginDetails} from "./model";
import {lastValueFrom} from "rxjs";

const URL_AUTHENTICATE_USER = '/authenticate';

@Injectable()
export class SudokuService {
  constructor(private http:HttpClient) {
  }

  postUserLogin(loginDetails: LoginDetails): Promise<string> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { username: loginDetails.username, password: loginDetails.password }

    console.info(body.toString());

    return lastValueFrom(this.http.post<string>(URL_AUTHENTICATE_USER,body,{headers}));
  }
}
