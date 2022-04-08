import { Component, OnInit } from '@angular/core';
import {Order} from "../../model/order";
import {OrderService} from "../../service/order.service";
import {OrderStatusService} from "../../service/order-status.service";
import {CartService} from "../../service/cart.service";

@Component({
  selector: 'app-order-page',
  templateUrl: './order-page.component.html',
  styleUrls: ['./order-page.component.less']
})
export class OrderPageComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrderService,
              private orderStatusService: OrderStatusService,
              private cartService: CartService) { }

  ngOnInit(): void {
    this.orderService.findOrdersByCurrentUser()
      .subscribe(orders => {
        this.orders = orders;
      }, err => {
        console.log(`Error retrieving orders ${err}`);
      });
    this.orderStatusService.onMessage('/order_out/order')
      .subscribe(msg => {
        let updated = this.orders.find(order => order.id === msg.orderid);
        if (updated) {
          updated.status = msg.status;
        }
      });
  }

  viewOrder() {
    // this.cartService.findAll()
    //   .subscribe(orders => {
    //     this.orders.find(order => order.id === orders.)
    //   });
  }

  deleteOrder() {
    this.orderService.deleteOrder(this.orders)
  }
}
