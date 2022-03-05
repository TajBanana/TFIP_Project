import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {LoginDetails, LoginResponse} from "../model";
import {UserService} from "../user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userLoginForm!: FormGroup;
  userLoginResponse: LoginResponse;
  error: boolean = false;
  errorMessage: string;
  username: string;
  userToken: string;


  constructor(private router: Router,
              private fb: FormBuilder,
              private userService: UserService) { }

  ngOnInit(): void {
    this.createLoginForm();
  }

  async loginUser() {
    const userDetails = this.userLoginForm.value as LoginDetails;
    console.info('user login details >>>> ' , userDetails);

    //try to log in user
    await this.userService
      .postUserLogin(userDetails)
      .then(r => {
        this.error = false;
        this.userLoginResponse = r;
        this.username = this.userLoginResponse.subject;
        this.userToken = this.userLoginResponse.token;
        this.userService.saveUser(this.username);
        this.userService.saveToken(this.userToken)
        this.home();})
      .catch(r => {
        this.userLoginResponse = r;
        this.error = true;
        console.info(this.userLoginResponse);
        this.errorMessage = 'incorrect user or password';
        this.userLoginForm.reset();
      });
  }

  private home() {
    this.router.navigate(['/']);
  }

  private createLoginForm() {
    this.userLoginForm = this.fb.group({
      username: this.fb.control(
        '',
        [Validators.minLength(3),
          Validators.maxLength(24),
          Validators.required]),
      password: this.fb.control(
        '',
        [Validators.required])
    })
  }
}
