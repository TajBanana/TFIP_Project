import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {LoginDetails} from "../model";
import {SudokuService} from "../sudoku.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userLoginForm!: FormGroup;
  userLoginResponse: string = '';

  constructor(private router: Router,
              private fb: FormBuilder,
              private sudokuService: SudokuService) { }

  ngOnInit(): void {
    this.createLoginForm();
  }

  async loginUser() {
    const userDetails = this.userLoginForm.value as LoginDetails;
    console.info('user login details >>>> ' , userDetails);
    await this.sudokuService
      .postUserLogin(userDetails)
      .then(r => this.userLoginResponse = r);
    console.info(this.userLoginResponse);
    this.userLoginForm.reset();
    this.home();
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
