#K-BOB: Korean Processed Food Vegan Scanner

K-BOB is an Android mobile application designed to help foreign vegetarians and vegans in Korea safely identify whether processed Korean foods align with their dietary restrictions. The app focuses on reducing language and cultural barriers through practical features such as OCR-based ingredient analysis, translation, and vegan-friendly food recommendations 


#Key Features

OCR-based ingredient detection from food package images
Dietary profile management (vegan, lacto, ovo, allergies, etc.)
Clear classification results: edible, not edible, or invalid input
Korean menu recommendations tailored to dietary preferences
Order translation support and nearby restaurant search via maps

#Technical Overview

Android application built with Java
OCR powered by Naver CLOVA OCR API
Network communication via Retrofit and OkHttp
External APIs used for OCR and mapping services


#My Contribution

 I primarily worked on integrating the OCR pipeline with the mobile application via external APIs.
This included:
#Designing the OCR request–response flow
#Connecting the app to the CLOVA OCR API
#Handling OCR results and mapping them to dietary classification logic
#Ensuring reliable API communication and user-facing result presentation

Project Context
This project was developed as part of a team software engineering course (CS550, KAIST), with an emphasis on realistic system design, external API usage, and user-centered feature refinement based on testing and feedback.
