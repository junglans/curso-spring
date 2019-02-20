import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  private author:any = {name: 'Juan Francisco', surname: "Jiménez Pérez"};

  constructor() {}

  ngOnInit() {}

}
