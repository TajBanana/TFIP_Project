import {AfterViewInit, Component, OnInit} from '@angular/core';
import {UserService} from "../user.service";
import {SudokuPuzzle} from "../model";

@Component({
  selector: 'app-userdetails',
  templateUrl: './userdetails.component.html',
  styleUrls: ['./userdetails.component.css']
})
export class UserdetailsComponent implements OnInit, AfterViewInit {

  puzzleList: SudokuPuzzle[]

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUserProfile(localStorage.getItem("auth-user")).then(
      r => {
        console.log('entered then of promise')
        this.puzzleList = r
        console.log('set puzzlelist')
        console.log(this.puzzleList)
      })
  }

  ngAfterViewInit(): void {
    console.log('FROM AFTER VIEW INIT >>>> ',this.puzzleList)
  }


}
