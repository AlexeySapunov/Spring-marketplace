import {Component, Input, OnInit} from '@angular/core';
import {LineItem} from "../../model/lineItem";

@Component({
  selector: 'app-product-cart',
  templateUrl: './product-cart.component.html',
  styleUrls: ['./product-cart.component.less']
})
export class ProductCartComponent implements OnInit {

  @Input() lineItems?: LineItem;

  constructor() { }

  ngOnInit(): void {
  }

}
