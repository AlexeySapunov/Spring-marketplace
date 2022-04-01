import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LineItem} from "../../model/lineItem";
import {CartService} from "../../service/cart.service";

@Component({
  selector: 'app-product-cart',
  templateUrl: './product-cart.component.html',
  styleUrls: ['./product-cart.component.less']
})
export class ProductCartComponent implements OnInit {

  @Output() updated = new EventEmitter();

  private _lineItem?: LineItem;

  qty: number = 0;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
  }

  @Input()
  set lineItem(value: LineItem | undefined) {
    this._lineItem = value;
    this.qty = value ? value.qty : 0;
  }

  get lineItem(): LineItem | undefined {
    return this._lineItem;
  }

  updateLineItem() {

  }

  deleteLineItem() {
    if (this._lineItem) {
      this.cartService.removeLineItem(this._lineItem)
        .subscribe(
          res => {
            this.updated.emit();
          },
          error => {
            console.log(error)
          }
        )
    }
  }

}
