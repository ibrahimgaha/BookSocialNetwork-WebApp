import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input() rating : number = 0
  maxRating : number = 5;


  get fullStars(): number { 

    return Math.floor(this.rating)

  }

  get halfStars(): Boolean { 

    return this.rating % 1 !==0;

  }

  get emptyStars(): number { 

    return this.maxRating - Math.ceil(this.rating);

  }

}
