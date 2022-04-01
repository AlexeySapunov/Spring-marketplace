import {Product} from "./product";

export class LineItem {

  constructor(public productId: number,
              public product: Product,
              public qty: number,
              public color: string,
              public material: string,
              public itemTotal: number) {
  }
}
