import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {SudokuService} from "../sudoku.service";
import {SudokuPuzzle, SudokuSolution} from "../model";

@Component({
  selector: 'app-puzzle',
  templateUrl: './puzzle.component.html',
  styleUrls: ['./puzzle.component.css']
})
export class PuzzleComponent implements OnInit {

  difficulty: string;
  username: string;
  puzzle: SudokuPuzzle;
  giveUp: boolean = false;
  sudokuSolution: SudokuSolution;

  constructor(private activatedRoute: ActivatedRoute,
              private sudokuService: SudokuService) { }

  ngOnInit(): void {
    this.difficulty = this.activatedRoute.snapshot.params['difficulty']
    console.log(this.difficulty)

    this.username = this.activatedRoute.snapshot.params['username'].slice(0,-1)
    console.log(this.username)

    this.sudokuService.getSudoku(this.difficulty)
      .then(r => {
        this.puzzle = r
        console.log(this.puzzle.puzzle)
        console.log(this.puzzle.difficulty)

      })
  }

  sudokuSolve() {
    this.sudokuService
      .solveSudoku(this.puzzle.puzzle)
      .then(r=> {
        this.giveUp = true;
        this.sudokuSolution = r;
        console.log(this.sudokuSolution)
      })
  }
}
