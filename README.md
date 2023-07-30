# SampleFoodTruckJC

<img src="/previews/food-truck.gif" align="right" width="320"/>

This project is aimed to demonstrate the modern declarative android Compose UIs.

This is for educational purposes only.

# Note

This is a clone version from [sample-food-truck](https://github.com/apple/sample-food-truck) which was written in SwiftUI.

The project was presented and associated with [WWDC22 session 110492](https://developer.apple.com/videos/wwdc2022).

For quick installation, please grab the latest version in the [Release](https://github.com/phatnhse/SampleFoodTruckJC/releases) section.

## Tech stack



- [Compose](https://developer.android.com/jetpack/compose): Jetpack Compose simplifies and accelerates UI development on Android. Write less code and use powerful tools and intuitive Kotlin APIs.
- [Lifecycle](https://developer.android.com/jetpack/compose/lifecycle): Observe Android lifecycles and handle UI states upon the lifecycle changes.
- [Navigation](https://developer.android.com/jetpack/compose/navigation): Navigate between composables while taking advantage of the Navigation component’s infrastructure and features.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
- [Animation Library](https://developer.android.com/jetpack/compose/animation/introduction): Explore  powerful and extensible APIs that make it easy to implement various animations in your app's UI.
- [Custom Layout](https://developer.android.com/jetpack/compose/layouts/custom): Build realistic and complex layout with `Layout` and `Modifier`.
- [Material You](https://m3.material.io/): Material 3 is the latest version of Google’s open-source design system. Design and build beautiful, usable products with Material 3.

<br/>
<br/>

## Custom Layouts and Animations

| Name                                                                                                                                                                                | Screenshot                                                        |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------|
| [BrandHeader](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/foodtruck/brand/BrandHeader.kt)                           | <img src="/previews/brand-header.png" width="42%">                |
| [DonutView](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/foodtruck/donut/DonutView.kt)                               | <img src="/previews/donut-view.png" width="42%">                  |
| [DonutBoxView](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/foodtruck/donut/DonutBoxView.kt)                         | <img src="/previews/donut-box-view.png" width="42%">              |
| [DonutStackView](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/foodtruck/donut/DonutStackView.kt)                     | <img src="/previews/donut-stack-view.png" width="42%">            |
| [DiagonalDonutStackLayout](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/foodtruck/donut/DiagonalDonutStackLayout.kt) | <img src="/previews/diagonal-donut-stack-layout.png" width="42%"> |
| [DonutGalleryGrid](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/donut/DonutGalleryGrid.kt)                           | <img src="/previews/donut-gallery.png" width="42%">               |
| [TruckOrdersCard](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/truck/cards/TruckOrdersCard.kt)                       | <img src="/previews/truck-orders-card.png" width="42%">           |
| [TruckDonutsCard](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/truck/cards/TruckDonutsCard.kt)                       | <img src="/previews/truck-donuts-card.png" width="42%">           |
| [TruckSocialFeedCard](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/truck/cards/TruckSocialFeedCard.kt)               | <img src="/previews/social-feed.png" width="42%">                 |
| [SalesHistoryLineChart](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/truck/SalesHistoryLineChart.kt)                 | <img src="/previews/line-chart.png" width="42%">                  |
| [DonutSalesBarChart](https://github.com/phatnhse/SampleFoodTruckJC/blob/main/app/src/main/java/com/phatnhse/sample_food_truck_jc/donut/DonutSalesBarChart.kt)                       | <img src="/previews/sales-bar-chart.png" width="42%">             |
