import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {LoginDetails, LoginResponse, Quote, SudokuPuzzle, SudokuSolution} from "./model";
import {lastValueFrom} from "rxjs";

const URL_GET_PUZZLE = '/sudoku';
const URL_GET_SOLUTION = '/sudoku/solve';

const URL_GET_QUOTE = '/test/quote';


@Injectable()
export class SudokuService {
  constructor(private http:HttpClient) {
  }

  getSudoku(difficulty: string): Promise<SudokuPuzzle> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const params = new HttpParams().set('difficulty', difficulty);
    return lastValueFrom(this.http.get<SudokuPuzzle>('/sudoku/' + difficulty, {headers, params}))
  }

  solveSudoku(sudoku: number[][]) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body =  {puzzle: sudoku};
    return lastValueFrom(this.http.post<SudokuSolution>(URL_GET_SOLUTION,body,{headers}))
  }

  getQuote(): Promise<Quote> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return lastValueFrom(this.http.get<Quote>(URL_GET_QUOTE,{headers}));
  }
}
