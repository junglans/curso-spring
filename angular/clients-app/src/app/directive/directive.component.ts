import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-directive',
  templateUrl: './directive.component.html'
})
export class DirectiveComponent implements OnInit {

  private courseList: string[] = ['TypeScript', 'JavaScript', 'Java', 'Python', 'C#']
  private enable:boolean = false;
  
  constructor() { }
  ngOnInit() {}

  public enableList(): void {
    this.enable = !this.enable;
  }
}
