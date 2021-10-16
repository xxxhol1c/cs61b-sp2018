# cs61b-sp2018
![GitHub last commit](https://img.shields.io/github/last-commit/xxxhol1c/cs61b-sp2018?color=9999FF)
## Project Progress 
- [x] Project 0
> Part ExtraCredit is not completed. It needs some creative ideas.  
> Maybe finish it later.
- [x] Project 1A   
>   The hardest part for me is the implement of the resize method.  
>   My tips:  Since the AD is circular, you need to consider two cases:  
>   
>   ` 1. the front index is smaller than the end, which means there is no circulation.`  
>   ` 2. the front index is larger, which means there is a circulation. `  
>   
>   To solve the case2: you need to separate the AD into two parts and respectively copy to the new AD.  
>   You also need to revise the index attribute. `Especially case1!` Do more tests `eg. remove and fill again` to make sure you implement doesn't pass the cases coincidentally.
