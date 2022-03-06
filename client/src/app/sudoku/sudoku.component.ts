import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-sudoku',
  templateUrl: './sudoku.component.html',
  styleUrls: ['./sudoku.component.css']
})
export class SudokuComponent implements OnInit {

  username: string;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  go(difficulty: string) {

    if (localStorage.getItem('auth-user')) {
      this.username = localStorage.getItem('auth-user');
    } else {
      this.username = 'guest'
    }

    this.router.navigate(['sudoku/', this.username + '/',difficulty + ''])
  }
}
