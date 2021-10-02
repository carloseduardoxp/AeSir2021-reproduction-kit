setwd(dirname(rstudioapi::getActiveDocumentContext()$path))
readDataSet = read.csv('readability.csv', header = TRUE, sep = ";", quote = "\"",
                dec = ".", fill = TRUE, comment.char = "")
undDataSet  = read.csv('understandability.csv', header = TRUE, sep = ";", quote = "\"",
                       dec = ".", fill = TRUE, comment.char = "")

#--------------------------------------------------------------------------------
#RQ1 - ranking independent variable
#RQ1 - readability
model=lm( readDataSet$readability ~ readDataSet$ranking )
anova(model)
ANOVA=aov(model)


TUKEY <- TukeyHSD(x=ANOVA, 'readDataSet$ranking', conf.level=0.95)
TUKEY
plot(TUKEY , las=1 , col="brown")

#RQ1 - understandability
model=lm( undDataSet$understandability ~ undDataSet$ranking )
anova(model)
ANOVA=aov(model)

TUKEY <- TukeyHSD(x=ANOVA, 'undDataSet$ranking', conf.level=0.95)
TUKEY
plot(TUKEY , las=1 , col="brown")

#--------------------------------------------------------------------------------
#RQ2 - general-purpose web search engines independent variable
#RQ2 - readability
model=lm( readDataSet$readability ~ readDataSet$site )
anova(model)
ANOVA=aov(model)

TUKEY <- TukeyHSD(x=ANOVA, 'readDataSet$site', conf.level=0.95)
TUKEY
plot(TUKEY ,  col="brown")


#RQ2 - understandability
model=lm( undDataSet$understandability ~ undDataSet$site )
anova(model)
ANOVA=aov(model)

TUKEY <- TukeyHSD(x=ANOVA, 'undDataSet$site', conf.level=0.95)
TUKEY
plot(TUKEY  , col="brown")

#--------------------------------------------------------------------------------
#RQ3 - recommended sites independent variable
#RQ3 - readability
model=lm( readDataSet$readability ~ readDataSet$dominio )
anova(model)
ANOVA=aov(model)

TUKEY <- TukeyHSD(x=ANOVA, 'readDataSet$dominio', conf.level=0.95)
plot(TUKEY , las=1 , col="brown")

#RQ3 - understandability
model=lm( undDataSet$understandability ~ undDataSet$dominio )
anova(model)
ANOVA=aov(model)

TUKEY <- TukeyHSD(x=ANOVA, 'undDataSet$dominio', conf.level=0.95)
par(mar=c(5,6,4,1)+.1)
plot(TUKEY , las=1 , col="brown")
