import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {LoginDetails, LoginResponse, Quote, SudokuPuzzle} from "./model";
import {lastValueFrom} from "rxjs";

const URL_GET_PUZZLE = '/sudoku';
const URL_GET_QUOTE = '/test/quote';


@Injectable()
export class SudokuService {
  constructor(private http:HttpClient) {
  }

  getSudoku(difficulty: string): Promise<SudokuPuzzle> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const params = new HttpParams().set('difficulty', difficulty);
    return lastValueFrom(this.http.get<SudokuPuzzle>(URL_GET_PUZZLE, {headers, params}))
  }

  getQuote(): Promise<Quote> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return lastValueFrom(this.http.get<Quote>(URL_GET_QUOTE,{headers}));
  }
}
