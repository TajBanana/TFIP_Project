import {Injectable} from "@angular/core";
import {LoginDetails, LoginResponse} from "./model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {lastValueFrom} from "rxjs";

const URL_AUTHENTICATE_USER = '/authenticate';
const URL_REGISTER_USER = '/register';


@Injectable()
export class UserService {


  constructor(private http: HttpClient) {
  }

  logOut() {
    localStorage.clear();
    window.sessionStorage.clear();
  }

  postUserLogin(loginDetails: LoginDetails): Promise<LoginResponse> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { username: loginDetails.username, password: loginDetails.password }
    return lastValueFrom(this.http.post<LoginResponse>(URL_AUTHENTICATE_USER,body,{headers}));
  }

  postUserRegister(loginDetails: LoginDetails): Promise<LoginResponse> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { username: loginDetails.username, password: loginDetails.password }
    return lastValueFrom(this.http.post<LoginResponse>(URL_REGISTER_USER,body,{headers}));
  }

  saveUser(user: string): void {
    localStorage.removeItem('auth-user')
    localStorage.setItem('auth-user', user)
    // window.sessionStorage.removeItem('auth-user');
    // window.sessionStorage.setItem('auth-user', user);
  }

  getUser(): string {
    // const user = window.sessionStorage.getItem('auth-user');
    const user = localStorage.getItem('auth-user');
    if (user) {
      return user;
    }
    return;
  }

  saveToken(token: string) {
    localStorage.removeItem('auth-token');
    localStorage.setItem('auth-token', token);
    // window.sessionStorage.removeItem('auth-token');
    // window.sessionStorage.setItem('auth-token', token);

  }

  getToken(): string {
    // const token = window.sessionStorage.getItem('auth-token');
    const token = localStorage.getItem('auth-token');
    if (token) {
      return token;
    }
    return;
  }
}
