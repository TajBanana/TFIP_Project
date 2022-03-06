import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { SudokuComponent } from './sudoku/sudoku.component';
import { UserdetailsComponent } from './userdetails/userdetails.component';
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";
import {ReactiveFormsModule} from "@angular/forms";
import {SudokuService} from "./sudoku.service";
import {UserService} from "./user.service";
import { PuzzleComponent } from './sudoku/puzzle.component';

const appRoutes = [
  {path:'', component:HomeComponent},
  {path:'login', component: LoginComponent},
  {path:'register', component: RegisterComponent},
  {path:'sudoku/:username', component: SudokuComponent},
  {path:'sudoku/'+':username'+'/:difficulty', component: PuzzleComponent},
  {path:'secure/:username', component:UserdetailsComponent},
  {path:'**', component: HomeComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    SudokuComponent,
    UserdetailsComponent,
    PuzzleComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    ReactiveFormsModule
  ],
  providers: [
    SudokuService,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
