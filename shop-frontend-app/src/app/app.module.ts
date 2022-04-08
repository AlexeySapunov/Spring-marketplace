import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductGalleryPageComponent } from './page/product-gallery-page/product-gallery-page.component';
import { ProductInfoPageComponent } from './page/product-info-page/product-info-page.component';
import { NavBarComponent } from './component/nav-bar/nav-bar.component';
import { FooterComponent } from './component/footer/footer.component';
import { ProductFilterComponent } from './component/product-filter/product-filter.component';
import { ProductGalleryComponent } from './component/product-gallery/product-gallery.component';
import { ProductPaginatorComponent } from './component/product-paginator/product-paginator.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { ProductCartComponent } from './component/product-cart/product-cart.component';
import { ProductCartPageComponent } from './page/product-cart-page/product-cart-page.component';
import { OrderPageComponent } from './page/order-page/order-page.component';
import { LoginPageComponent } from './page/login-page/login-page.component';
import {UnauthorizedInterceptor} from "./helper/unauthorized-interceptor";

@NgModule({
  declarations: [
    AppComponent,
    ProductGalleryPageComponent,
    ProductInfoPageComponent,
    NavBarComponent,
    FooterComponent,
    ProductFilterComponent,
    ProductGalleryComponent,
    ProductPaginatorComponent,
    ProductCartComponent,
    ProductCartPageComponent,
    OrderPageComponent,
    LoginPageComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
    ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: UnauthorizedInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
