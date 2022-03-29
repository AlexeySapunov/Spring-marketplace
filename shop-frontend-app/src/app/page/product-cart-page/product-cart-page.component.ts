import { Component, OnInit } from '@angular/core';
import {LineItem} from "../../model/lineItem";
import {CartService} from "../../service/cart.service";

@Component({
  selector: 'app-product-cart-page',
  templateUrl: './product-cart-page.component.html',
  styleUrls: ['./product-cart-page.component.less']
})
export class ProductCartPageComponent implements OnInit {

  lineItems?: LineItem;

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.cartService.findAll().subscribe(res => {
      console.log("Loading products..")
      this.lineItems = res.lineItems;
    }, error => {
      console.log(`Error loading products ${error}`);
    });
  }
}
