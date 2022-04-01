import {LineItem} from "./lineItem";


export class AllCart {

  constructor(public lineItems: LineItem[],
              public subtotal: number) {
  }
}
