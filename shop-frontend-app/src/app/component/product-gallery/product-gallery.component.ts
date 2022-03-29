import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {CartService} from "../../service/cart.service";
import {AddLineItem} from "../../model/addLineItem";

@Component({
  selector: 'app-product-gallery',
  templateUrl: './product-gallery.component.html',
  styleUrls: ['./product-gallery.component.less']
})
export class ProductGalleryComponent implements OnInit {

  @Input() products: Product[] = [];

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
  }

  addToCart(id: number) {
    this.cartService.addToCart(new AddLineItem(id, 1, "", "")).subscribe();
  }
}
