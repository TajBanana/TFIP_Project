export interface LoginDetails {
  username: string;
  password: string
}

export interface LoginResponse {
  subject: string;
  token: string
}

export interface SudokuPuzzle {
  puzzle: number [][];
  difficulty: string
}

export interface SudokuSolution {
  solution: number [][]
}

export interface Quote {
  quote: string;
}
