import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SudokuSolution} from "../model";

@Component({
  selector: 'app-uploadimage',
  templateUrl: './uploadimage.component.html',
  styleUrls: ['./uploadimage.component.css']
})
export class UploadimageComponent implements OnInit {

  selectedFile: File = null;
  message: String;
  uploadStatus: boolean = false;
  giveUp: boolean = false;
  sudokuSolution: SudokuSolution;

  constructor(private http:HttpClient) { }

  ngOnInit(): void {
  }

  onFileSelect(event) {
    this.selectedFile = <File>event.target.files[0]
  }


  onUpload() {
    console.log('selected file >>>> ' + this.selectedFile)
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    this.http.post('/sudoku/solveimage', uploadImageData, { observe: 'response' })
      .subscribe((response) => {
          if (response.status === 200) {
            this.uploadStatus = true;
            this.giveUp = true;
            this.sudokuSolution = response.body as SudokuSolution;
            this.message = 'Image uploaded successfully';
          } else {
            this.message = 'Image not uploaded successfully';
          }
        }
      );
  }
}
