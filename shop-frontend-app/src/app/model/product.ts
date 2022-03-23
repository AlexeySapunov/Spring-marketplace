import {Category} from "./category";
import {Brand} from "./brand";

export class Product {

  constructor(public id: number,
              public name: string,
              public description: string,
              public price: number,
              public category: Category,
              public pictures: number[],
              public brand: Brand) {
  }
}
