import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../user.service";
import {LoginDetails} from "../model";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerUserForm!: FormGroup;
  error: boolean = false;

  constructor(private router: Router,
              private fb: FormBuilder,
              private userService: UserService) { }

  ngOnInit(): void {
    this.createRegisterForm()
  }

  registerUser() {
    const userDetails =  this.registerUserForm.value as LoginDetails;
    console.info('register user details >>>> ', userDetails)
    this.userService.postUserRegister(userDetails)
      .then( () => {
        console.log("register success");
        alert('Successfully registered, please login.')
        this.router.navigate(['/login'])

      })
      .catch(() => {
        this.error = true
        this.registerUserForm.reset()
      });
  }

  private createRegisterForm() {
    this.registerUserForm=this.fb.group({
      username: this.fb.control('',
        [Validators.minLength(3),
          Validators.maxLength(24),
          Validators.required]),
      password: this.fb.control('',[
        Validators.minLength(6),
        Validators.maxLength(40),
        Validators.required])
    })
  }
}
