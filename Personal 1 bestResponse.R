
t_col <- function(color, percent = 50) {
  rgb.val <- col2rgb(color)
  t.col <- rgb(rgb.val[1], rgb.val[2], rgb.val[3],
               max = 255,
               alpha = (100-percent)*255/100)
  
}
mycol <- t_col("magenta2", perc = 70)

#--------------------------------------------------- AGC ---------------------------------------------------
AGC_1_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-agc-0.1-1-no.28 is 0.1.csv",header = T)
AGC_1_T_ALL_1 <- rowMeans(AGC_1_ALL_1[,1:30])
AGC_1_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-agc-0.1-1-no.28 is 0.8.csv",header = T)
AGC_1_T_ONE_8 <- rowMeans(AGC_1_ONE_8[,1:30])
AGC_1_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-agc-0.8-1-no.28 is 0.1.csv",header = T)
AGC_1_T_ONE_1 <- rowMeans(AGC_1_ONE_1[,1:30])
AGC_1_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-agc-0.8-1-no.28 is 0.8.csv",header = T)
AGC_1_T_ALL_8 <- rowMeans(AGC_1_ALL_8[,1:30])

plot(AGC_1_T_ONE_1,type="l",col="deepskyblue1", lty=2, lwd=2,xlab="Iteration", ylab="Personal Cost",xlim=c(0, 1000), ylim=c(80, 185))
lines(AGC_1_T_ALL_8,type="l",col="firebrick2", lty=2, lwd=2)
lines(AGC_1_T_ALL_1,type="l",col="seagreen3",lty=5, lwd=2)
lines(AGC_1_T_ONE_8,type="l",col="darkorange", lty=4, lwd=2)
abline(h=179.733,type="l",col=mycol,alpha = 0.1, lty=1, lwd=2)
title(main='Personal Cost, AGC Type 1',xlab="Iteration", ylab="Personal Cost",lwd=3)

#--------------------------------------------------- BI ---------------------------------------------------
BI_1_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_BI-0.1-1-no.28 is 0.1.csv",header = T)
BI_1_T_ALL_1 <- rowMeans(BI_1_ALL_1[,1:30])
BI_1_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_BI-0.1-1-no.28 is 0.8.csv",header = T)
BI_1_T_ONE_8 <- rowMeans(BI_1_ONE_8[,1:30])
BI_1_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_BI-0.8-1-no.28 is 0.1.csv",header = T)
BI_1_T_ONE_1 <- rowMeans(BI_1_ONE_1[,1:30])
BI_1_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_BI-0.8-1-no.28 is 0.8.csv",header = T)
BI_1_T_ALL_8 <- rowMeans(BI_1_ALL_8[,1:30])

plot(BI_1_T_ONE_1,type="l",col="deepskyblue1", lty=2, lwd=2,xlab="Iteration", ylab="Personal Cost",xlim=c(0, 1000), ylim=c(80, 185))
lines(BI_1_T_ALL_8,type="l",col="firebrick2", lty=2, lwd=2)
lines(BI_1_T_ALL_1,type="l",col="seagreen3",lty=5, lwd=2)
lines(BI_1_T_ONE_8,type="l",col="darkorange", lty=4, lwd=2)
abline(h=179.733,type="l",col=mycol, lty=1, lwd=2)
title(main='Personal Cost, SM_AGC_BI Type 1',xlab="Iteration", ylab="Personal Cost",lwd=3)

#--------------------------------------------------- CI ---------------------------------------------------
CI_1_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_CI-0.1-1-no.28 is 0.1.csv",header = T)
CI_1_T_ALL_1 <- rowMeans(CI_1_ALL_1[,1:30])
CI_1_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_CI-0.1-1-no.28 is 0.8.csv",header = T)
CI_1_T_ONE_8 <- rowMeans(CI_1_ONE_8[,1:30])
CI_1_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_CI-0.8-1-no.28 is 0.1.csv",header = T)
CI_1_T_ONE_1 <- rowMeans(CI_1_ONE_1[,1:30])
CI_1_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_CI-0.8-1-no.28 is 0.8.csv",header = T)
CI_1_T_ALL_8 <- rowMeans(CI_1_ALL_8[,1:30])

plot(CI_1_T_ONE_1,type="l",col="deepskyblue1", lty=2, lwd=2,xlab="Iteration", ylab="Personal Cost",xlim=c(0, 1000), ylim=c(80, 185))
lines(CI_1_T_ALL_8,type="l",col="firebrick2", lty=2, lwd=2)
lines(CI_1_T_ALL_1,type="l",col="seagreen3",lty=5, lwd=2)
lines(CI_1_T_ONE_8,type="l",col="darkorange", lty=4, lwd=2)
abline(h=179.733,type="l",col=mycol, lty=1, lwd=2)
title(main='Personal Cost, SM_AGC_CI Type 1',xlab="Iteration", ylab="Personal Cost",lwd=3)

#--------------------------------------------------- T_BI ---------------------------------------------------
T_BI_1_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T_BI-0.1-1-no.28 is 0.1.csv",header = T)
T_BI_1_T_ALL_1 <- rowMeans(T_BI_1_ALL_1[,1:30])
T_BI_1_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T_BI-0.1-1-no.28 is 0.8.csv",header = T)
T_BI_1_T_ONE_8 <- rowMeans(T_BI_1_ONE_8[,1:30])
T_BI_1_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T_BI-0.8-1-no.28 is 0.1.csv",header = T)
T_BI_1_T_ONE_1 <- rowMeans(T_BI_1_ONE_1[,1:30])
T_BI_1_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T_BI-0.8-1-no.28 is 0.8.csv",header = T)
T_BI_1_T_ALL_8 <- rowMeans(T_BI_1_ALL_8[,1:30])

plot(T_BI_1_T_ONE_1,type="l",col="deepskyblue1", lty=2, lwd=2,xlab="Iteration", ylab="Personal Cost",xlim=c(0, 1000), ylim=c(80, 185))
lines(T_BI_1_T_ALL_8,type="l",col="firebrick2", lty=2, lwd=2)
lines(T_BI_1_T_ALL_1,type="l",col="seagreen3",lty=5, lwd=2)
lines(T_BI_1_T_ONE_8,type="l",col="darkorange", lty=4, lwd=2)
abline(h=179.733,type="l",col="magenta2", lty=1, lwd=2)
title(main='Personal Cost, SM_AGC_T_BI Type 1',xlab="Iteration", ylab="Personal Cost",lwd=3)

#--------------------------------------------------- T_CI ---------------------------------------------------
T_CI_1_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T_CI-0.1-1-no.28 is 0.1.csv",header = T)
T_CI_1_T_ALL_1 <- rowMeans(T_CI_1_ALL_1[,1:30])
T_CI_1_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T_CI-0.1-1-no.28 is 0.8.csv",header = T)
T_CI_1_T_ONE_8 <- rowMeans(T_CI_1_ONE_8[,1:30])
T_CI_1_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T_CI-0.8-1-no.28 is 0.1.csv",header = T)
T_CI_1_T_ONE_1 <- rowMeans(T_CI_1_ONE_1[,1:30])
T_CI_1_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T_CI-0.8-1-no.28 is 0.8.csv",header = T)
T_CI_1_T_ALL_8 <- rowMeans(T_CI_1_ALL_8[,1:30])

plot(T_CI_1_T_ONE_1,type="l",col="deepskyblue1", lty=2, lwd=2,xlab="Iteration", ylab="Personal Cost",xlim=c(0, 1000), ylim=c(80, 185))
lines(T_CI_1_T_ALL_8,type="l",col="firebrick2", lty=2, lwd=2)
lines(T_CI_1_T_ALL_1,type="l",col="seagreen3",lty=5, lwd=2)
lines(T_CI_1_T_ONE_8,type="l",col="darkorange", lty=4, lwd=2)
abline(h=179.733,type="l",col="magenta2", lty=1, lwd=2)
title(main='Personal Cost, SM_AGC_T_CI Type 1',xlab="Iteration", ylab="Personal Cost",lwd=3)

#--------------------------------------------------- T_ ---------------------------------------------------
T_1_ALL_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T-0.1-1-no.28 is 0.1.csv",header = T)
T_1_T_ALL_1 <- rowMeans(T_1_ALL_1[,1:30])
T_1_ONE_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T-0.1-1-no.28 is 0.8.csv",header = T)
T_1_T_ONE_8 <- rowMeans(T_1_ONE_8[,1:30])
T_1_ONE_1 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T-0.8-1-no.28 is 0.1.csv",header = T)
T_1_T_ONE_1 <- rowMeans(T_1_ONE_1[,1:30])
T_1_ALL_8 <- read.csv("C:/MARTIN/BEER SHEVA/מערכת/Basics/a-1/personal sum-SM_AGC_T-0.8-1-no.28 is 0.8.csv",header = T)
T_1_T_ALL_8 <- rowMeans(T_1_ALL_8[,1:30])

plot(T_1_T_ONE_1,type="l",col="deepskyblue1", lty=2, lwd=2,xlab="Iteration", ylab="Personal Cost",xlim=c(0, 1000), ylim=c(80, 185))
lines(T_1_T_ALL_8,type="l",col="firebrick2", lty=2, lwd=2)
lines(T_1_T_ALL_1,type="l",col="seagreen3",lty=5, lwd=2)
lines(T_1_T_ONE_8,type="l",col="darkorange", lty=4, lwd=2)
abline(h=179.733,type="l",col="magenta2", lty=1, lwd=2)
title(main='Personal Cost, SM_AGC_T Type 1',xlab="Iteration", ylab="Personal Cost",lwd=3)
