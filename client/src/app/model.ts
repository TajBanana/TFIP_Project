export interface LoginDetails {
  username: string;
  password: string
}

export interface LoginResponse {
  subject: string;
  token: string
}

export interface SudokuPuzzle {
  puzzle: string;
  difficulty: string
}

export interface Quote {
  quote: string;
}
