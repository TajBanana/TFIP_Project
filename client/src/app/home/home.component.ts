import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {UserService} from "../user.service";
import {SudokuService} from "../sudoku.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  quote: string;
  username: string;
  subscription: Subscription;

  constructor(private router: Router,
              private userService: UserService,
              private sudokuService: SudokuService
              ) {}

  ngOnInit(): void {
    // TODO UNCOMMENT THIS TO GET QUOTE
/*    this.sudokuService.getQuote()
      .then(q => this.quote = q.quote);*/

    if (this.userService.getUser()) {
      this.username = this.userService.getUser()
    } else {
      this.username = 'guest';
    }
  }

  login() {
    this.router.navigate(['/login'])
  }

  register() {
    this.router.navigate(['/register'])
  }

  newGame() {
    this.router.navigate(['/sudoku', 'username'])
  }

  logOut() {
    this.userService.logOut();
    this.router.navigate(['/'])
      .then(() => {
        window.location.reload();
      });
  }

  userProfile() {
    this.router.navigate(['/secure', 'username'])
  }

  onUpload() {
    this.router.navigate(['/upload'])
  }
}
