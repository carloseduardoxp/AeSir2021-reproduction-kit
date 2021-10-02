# Create data for the graph.
x <- c(15704,4956,1696,1339,668,691,524,406,962)
labels <- c(1,0.93,0.86,0.8,0.73,0.66,0.6,0,53,"lower than 0.53")

piepercent<- round(100*x/sum(x), 1)

# Plot the chart.
pie(x, labels = paste(piepercent,"%"), main = "Understandability",col = gray.colors(length(x)))


legend("topright", c("Score 1","Score 0.93","Score 0.86","Score 0.8","Score 0.73",
                     "Score 0.66","Score 0.6","Score 0.53","Score < 0.53"), cex = 0.8,
       fill = gray.colors(length(x)))

# Create data for the graph.
x1 <- c(27,3)

piepercent1<- round(100*x1/sum(x1), 1)

# Plot the chart.
pie(x1, labels = paste(piepercent1,"%"), main = "City pie chart",col = gray.colors(length(x1)))


legend("topright", c("Top 1","Top 2"), cex = 0.8,
       fill = gray.colors(length(x1)))


# Create data for the graph.
x2 <- c(30)

piepercent1<- round(100*x2/sum(x2), 1)

# Plot the chart.
pie(x2, labels = paste(piepercent1,"%"), main = "City pie chart",col = gray.colors(length(x2)))


legend("topright", c("stackoverflow.com"), cex = 0.8,
       fill = gray.colors(length(x2)))


# Create data for the graph.
x3 <- c(14,5,4,1,6)

piepercent3<- round(100*x3/sum(x3), 1)

# Plot the chart.
pie(x3, labels = paste(piepercent3,"%"), main = "City pie chart",col = gray.colors(length(x3)))


legend("topright", c("Top 1","Top 2","Top 4","Top 5","Top 10"), cex = 0.8,
       fill = gray.colors(length(x3)))
       