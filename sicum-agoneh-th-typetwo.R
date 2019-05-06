Social_Beginning <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/agc-0.1-2-no.28 is 0.1.csv",header = T)
Personal_Beginning <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-agc-0.1-2-no.28 is 0.1.csv",header = T)
avg_Social_Beginning <- rowMeans(Social_Beginning[1,1:30])
avg_Personal_Beginning <- rowMeans(Personal_Beginning[1,1:30])


G_AGC_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/agc-0.1-2-no.28 is 0.1.csv",header = T)
G_AGC_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/agc-0.1-2-no.28 is 0.8.csv",header = T)
G_AGC_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/agc-0.8-2-no.28 is 0.1.csv",header = T)
G_AGC_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/agc-0.8-2-no.28 is 0.8.csv",header = T)
G_AGC_2_T_ALL_1 <- rowMeans(G_AGC_2_ALL_1[1000,1:30])
G_AGC_2_T_ONE_8 <- rowMeans(G_AGC_2_ONE_8[1000,1:30])
G_AGC_2_T_ONE_1 <- rowMeans(G_AGC_2_ONE_1[1000,1:30])
G_AGC_2_T_ALL_8 <- rowMeans(G_AGC_2_ALL_8[1000,1:30])
G_BI_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_BI-0.1-2-no.28 is 0.1.csv",header = T)
G_BI_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_BI-0.1-2-no.28 is 0.8.csv",header = T)
G_BI_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_BI-0.8-2-no.28 is 0.1.csv",header = T)
G_BI_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_BI-0.8-2-no.28 is 0.8.csv",header = T)
G_BI_2_T_ALL_1 <- rowMeans(G_BI_2_ALL_1[1000,1:30])
G_BI_2_T_ONE_8 <- rowMeans(G_BI_2_ONE_8[1000,1:30])
G_BI_2_T_ONE_1 <- rowMeans(G_BI_2_ONE_1[1000,1:30])
G_BI_2_T_ALL_8 <- rowMeans(G_BI_2_ALL_8[1000,1:30])
G_CI_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_CI-0.1-2-no.28 is 0.1.csv",header = T)
G_CI_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_CI-0.1-2-no.28 is 0.8.csv",header = T)
G_CI_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_CI-0.8-2-no.28 is 0.1.csv",header = T)
G_CI_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_CI-0.8-2-no.28 is 0.8.csv",header = T)
G_CI_2_T_ALL_1 <- rowMeans(G_CI_2_ALL_1[1000,1:30])
G_CI_2_T_ONE_8 <- rowMeans(G_CI_2_ONE_8[1000,1:30])
G_CI_2_T_ONE_1 <- rowMeans(G_CI_2_ONE_1[1000,1:30])
G_CI_2_T_ALL_8 <- rowMeans(G_CI_2_ALL_8[1000,1:30])
G_T_BI_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T_BI-0.1-2-no.28 is 0.1.csv",header = T)
G_T_BI_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T_BI-0.1-2-no.28 is 0.8.csv",header = T)
G_T_BI_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T_BI-0.8-2-no.28 is 0.1.csv",header = T)
G_T_BI_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T_BI-0.8-2-no.28 is 0.8.csv",header = T)
G_T_BI_2_T_ALL_1 <- rowMeans(G_T_BI_2_ALL_1[1000,1:30])
G_T_BI_2_T_ONE_8 <- rowMeans(G_T_BI_2_ONE_8[1000,1:30])
G_T_BI_2_T_ONE_1 <- rowMeans(G_T_BI_2_ONE_1[1000,1:30])
G_T_BI_2_T_ALL_8 <- rowMeans(G_T_BI_2_ALL_8[1000,1:30])
G_T_CI_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T_CI-0.1-2-no.28 is 0.1.csv",header = T)
G_T_CI_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T_CI-0.1-2-no.28 is 0.8.csv",header = T)
G_T_CI_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T_CI-0.8-2-no.28 is 0.1.csv",header = T)
G_T_CI_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T_CI-0.8-2-no.28 is 0.8.csv",header = T)
G_T_CI_2_T_ALL_1 <- rowMeans(G_T_CI_2_ALL_1[1000,1:30])
G_T_CI_2_T_ONE_8 <- rowMeans(G_T_CI_2_ONE_8[1000,1:30])
G_T_CI_2_T_ONE_1 <- rowMeans(G_T_CI_2_ONE_1[1000,1:30])
G_T_CI_2_T_ALL_8 <- rowMeans(G_T_CI_2_ALL_8[1000,1:30])
G_T_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T-0.1-2-no.28 is 0.1.csv",header = T)
G_T_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T-0.1-2-no.28 is 0.8.csv",header = T)
G_T_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T-0.8-2-no.28 is 0.1.csv",header = T)
G_T_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/SM_AGC_T-0.8-2-no.28 is 0.8.csv",header = T)
G_T_2_T_ALL_1 <- rowMeans(G_T_2_ALL_1[1000,1:30])
G_T_2_T_ONE_8 <- rowMeans(G_T_2_ONE_8[1000,1:30])
G_T_2_T_ONE_1 <- rowMeans(G_T_2_ONE_1[1000,1:30])
G_T_2_T_ALL_8 <- rowMeans(G_T_2_ALL_8[1000,1:30])

#^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
#^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
AGC_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-agc-0.1-2-no.28 is 0.1.csv",header = T)
AGC_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-agc-0.1-2-no.28 is 0.8.csv",header = T)
AGC_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-agc-0.8-2-no.28 is 0.1.csv",header = T)
AGC_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-agc-0.8-2-no.28 is 0.8.csv",header = T)
AGC_2_T_ALL_1 <- rowMeans(AGC_2_ALL_1[1000,1:30])
AGC_2_T_ONE_8 <- rowMeans(AGC_2_ONE_8[1000,1:30])
AGC_2_T_ONE_1 <- rowMeans(AGC_2_ONE_1[1000,1:30])
AGC_2_T_ALL_8 <- rowMeans(AGC_2_ALL_8[1000,1:30])
BI_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_BI-0.1-2-no.28 is 0.1.csv",header = T)
BI_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_BI-0.1-2-no.28 is 0.8.csv",header = T)
BI_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_BI-0.8-2-no.28 is 0.1.csv",header = T)
BI_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_BI-0.8-2-no.28 is 0.8.csv",header = T)
BI_2_T_ALL_1 <- rowMeans(BI_2_ALL_1[1000,1:30])
BI_2_T_ONE_8 <- rowMeans(BI_2_ONE_8[1000,1:30])
BI_2_T_ONE_1 <- rowMeans(BI_2_ONE_1[1000,1:30])
BI_2_T_ALL_8 <- rowMeans(BI_2_ALL_8[1000,1:30])
CI_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_CI-0.1-2-no.28 is 0.1.csv",header = T)
CI_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_CI-0.1-2-no.28 is 0.8.csv",header = T)
CI_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_CI-0.8-2-no.28 is 0.1.csv",header = T)
CI_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_CI-0.8-2-no.28 is 0.8.csv",header = T)
CI_2_T_ALL_1 <- rowMeans(CI_2_ALL_1[1000,1:30])
CI_2_T_ONE_8 <- rowMeans(CI_2_ONE_8[1000,1:30])
CI_2_T_ONE_1 <- rowMeans(CI_2_ONE_1[1000,1:30])
CI_2_T_ALL_8 <- rowMeans(CI_2_ALL_8[1000,1:30])
T_BI_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T_BI-0.1-2-no.28 is 0.1.csv",header = T)
T_BI_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T_BI-0.1-2-no.28 is 0.8.csv",header = T)
T_BI_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T_BI-0.8-2-no.28 is 0.1.csv",header = T)
T_BI_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T_BI-0.8-2-no.28 is 0.8.csv",header = T)
T_BI_2_T_ALL_1 <- rowMeans(T_BI_2_ALL_1[1000,1:30])
T_BI_2_T_ONE_8 <- rowMeans(T_BI_2_ONE_8[1000,1:30])
T_BI_2_T_ONE_1 <- rowMeans(T_BI_2_ONE_1[1000,1:30])
T_BI_2_T_ALL_8 <- rowMeans(T_BI_2_ALL_8[1000,1:30])
T_CI_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T_CI-0.1-2-no.28 is 0.1.csv",header = T)
T_CI_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T_CI-0.1-2-no.28 is 0.8.csv",header = T)
T_CI_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T_CI-0.8-2-no.28 is 0.1.csv",header = T)
T_CI_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T_CI-0.8-2-no.28 is 0.8.csv",header = T)
T_CI_2_T_ALL_1 <- rowMeans(T_CI_2_ALL_1[1000,1:30])
T_CI_2_T_ONE_8 <- rowMeans(T_CI_2_ONE_8[1000,1:30])
T_CI_2_T_ONE_1 <- rowMeans(T_CI_2_ONE_1[1000,1:30])
T_CI_2_T_ALL_8 <- rowMeans(T_CI_2_ALL_8[1000,1:30])
T_2_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T-0.1-2-no.28 is 0.1.csv",header = T)
T_2_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T-0.1-2-no.28 is 0.8.csv",header = T)
T_2_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T-0.8-2-no.28 is 0.1.csv",header = T)
T_2_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/maarejet/Basics/100 agents 0.1/type 2/personal sum-SM_AGC_T-0.8-2-no.28 is 0.8.csv",header = T)
T_2_T_ALL_1 <- rowMeans(T_2_ALL_1[1000,1:30])
T_2_T_ONE_8 <- rowMeans(T_2_ONE_8[1000,1:30])
T_2_T_ONE_1 <- rowMeans(T_2_ONE_1[1000,1:30])
T_2_T_ALL_8 <- rowMeans(T_2_ALL_8[1000,1:30])

#&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
#&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
avg_Social_Beginning

#--------------------------------------------------- AGC - Social ---------------------------------------------------
G_AGC_2_T_ALL_1
G_AGC_2_T_ONE_8
G_AGC_2_T_ONE_1
G_AGC_2_T_ALL_8
#--------------------------------------------------- BI - Social ---------------------------------------------------
G_BI_2_T_ALL_1
G_BI_2_T_ONE_8
G_BI_2_T_ONE_1
G_BI_2_T_ALL_8
#--------------------------------------------------- CI - Social ---------------------------------------------------
G_CI_2_T_ALL_1
G_CI_2_T_ONE_8
G_CI_2_T_ONE_1
G_CI_2_T_ALL_8
#--------------------------------------------------- T_BI - Social ---------------------------------------------------
G_T_BI_2_T_ALL_1
G_T_BI_2_T_ONE_8
G_T_BI_2_T_ONE_1
G_T_BI_2_T_ALL_8

#--------------------------------------------------- T_CI - Social ---------------------------------------------------
G_T_CI_2_T_ALL_1
G_T_CI_2_T_ONE_8
G_T_CI_2_T_ONE_1
G_T_CI_2_T_ALL_8

#--------------------------------------------------- T_ - Social ---------------------------------------------------
G_T_2_T_ALL_1
G_T_2_T_ONE_8
G_T_2_T_ONE_1
G_T_2_T_ALL_8

#&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
#&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
avg_Personal_Beginning

#--------------------------------------------------- AGC - Personal ---------------------------------------------
AGC_2_T_ALL_1
AGC_2_T_ONE_8
AGC_2_T_ONE_1
AGC_2_T_ALL_8
#--------------------------------------------------- BI  - Personal ---------------------------------------------------
BI_2_T_ALL_1
BI_2_T_ONE_8
BI_2_T_ONE_1
BI_2_T_ALL_8
#--------------------------------------------------- CI  - Personal ---------------------------------------------------
CI_2_T_ALL_1
CI_2_T_ONE_8
CI_2_T_ONE_1
CI_2_T_ALL_8
#--------------------------------------------------- T_BI  - Personal ---------------------------------------------------
T_BI_2_T_ALL_1
T_BI_2_T_ONE_8
T_BI_2_T_ONE_1
T_BI_2_T_ALL_8
#--------------------------------------------------- T_CI  - Personal ---------------------------------------------------
T_CI_2_T_ALL_1
T_CI_2_T_ONE_8
T_CI_2_T_ONE_1
T_CI_2_T_ALL_8
#--------------------------------------------------- T_  - Personal ---------------------------------------------------
T_2_T_ALL_1
T_2_T_ONE_8
T_2_T_ONE_1
T_2_T_ALL_8

