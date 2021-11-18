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
- [x] Project 1B && Porject 1Gold
- [x] Project 2 && Project 2 Gold
>   My tips:   
>   Phase 1: Generate the world   
>   `Step1: Add random rooms in the world. (make some restricted condition).`  
>   `Step2: Connect rooms with hallway (a special room).`  
>   `Step3: Add some customized elements.` 
>   
>   Phase 2: Interact   
>   `Add mouse UI (revise the render method to avoid text flashing.)`    
>    `Save and load game.`   
>    `Move and other control.` 

>   Gold Point:   
>   `Set the snake and herb to change the health condition.`  
>   `Set the treasure (some contain the keys, press 'o' to get)`  
>   `Set the light source mode to simulate the view of player.`  
>   `When you loose your HP, the player would turn to red. And when you get HP, it turn to green.`  
>   ` You need more than three keys to open the door.`
