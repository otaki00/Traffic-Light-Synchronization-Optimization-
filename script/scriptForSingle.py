# import pandas as pd
# import matplotlib.pyplot as plt

# # Load the CSV file into a DataFrame
# file_path = 'ga_performance.csv'  # Replace with your file path
# df = pd.read_csv(file_path)

# # Ensure the file has columns named 'Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value'
# df.columns = ['Generation', 'Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value']



# # Calculate initial and final values
# initial_best = df['Best Fitness Values'].iloc[0]
# final_best = df['Best Fitness Values'].iloc[-1]

# initial_average = df['Average Fitness Value'].iloc[0]
# final_average = df['Average Fitness Value'].iloc[-1]

# initial_worst = df['Worst Fitness Value'].iloc[0]
# final_worst = df['Worst Fitness Value'].iloc[-1]

# # Calculate percentage improvements
# best_improvement = ((final_best - initial_best) / initial_best) * 100
# average_improvement = ((final_average - initial_average) / initial_average) * 100
# worst_improvement = ((final_worst - initial_worst) / initial_worst) * 100

# # Print results
# print(f"Best Fitness Improvement: {best_improvement:.2f}%")
# print(f"Average Fitness Improvement: {average_improvement:.2f}%")
# print(f"Worst Fitness Improvement: {worst_improvement:.2f}%")

# # Plot the convergence rate
# plt.figure(figsize=(10, 6))
# plt.plot(df['Generation'], df['Best Fitness Values'], label='Best Fitness')
# plt.plot(df['Generation'], df['Average Fitness Value'], label='Average Fitness')
# plt.plot(df['Generation'], df['Worst Fitness Value'], label='Worst Fitness')

# plt.xlabel('Generation')
# plt.ylabel('Fitness Value')
# plt.title('Convergence Rate of Fitness Values')
# plt.legend()
# plt.grid(True)
# plt.show()


# import pandas as pd
# import matplotlib.pyplot as plt

# # Load the CSV file into a DataFrame
# file_path = 'ga_performance.csv'  # Replace with your file path
# df = pd.read_csv(file_path)

# # Ensure the file has columns named 'Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value'
# df.columns = ['Generation', 'Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value']


# # Add a 'Generation' column for the x-axis
# # df['Generation'] = df.index + 1

# # Calculate standard deviation and variance across fitness values for each generation
# df['Standard Deviation'] = df[['Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value']].std(axis=1)
# df['Variance'] = df[['Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value']].var(axis=1)

# # Plot standard deviation and variance
# plt.figure(figsize=(10, 6))
# plt.plot(df['Generation'], df['Standard Deviation'], label='Standard Deviation')
# plt.plot(df['Generation'], df['Variance'], label='Variance')

# plt.xlabel('Generation')
# plt.ylabel('Value')
# plt.title('Standard Deviation and Variance of Fitness Values')
# plt.legend()
# plt.grid(True)
# plt.show()

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Load the CSV file into a DataFrame
file_path = 'ga_performance.csv'  # Replace with your file path
df = pd.read_csv(file_path)

# Ensure the file has columns named 'Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value'
df.columns = ['Generation', 'Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value']


# Add a 'Generation' column for the x-axis
df['Generation'] = df.index + 1

# Calculate standard deviation and variance
df['Standard Deviation'] = df[['Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value']].std(axis=1)
df['Variance'] = df[['Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value']].var(axis=1)

# Calculate convergence rate (rate of change in best fitness values)
df['Convergence Rate'] = df['Best Fitness Values'].pct_change()

# Plot the convergence rate
plt.figure(figsize=(10, 6))
plt.plot(df['Generation'], df['Best Fitness Values'], label='Best Fitness')
plt.plot(df['Generation'], df['Average Fitness Value'], label='Average Fitness')
plt.plot(df['Generation'], df['Worst Fitness Value'], label='Worst Fitness')

plt.xlabel('Generation')
plt.ylabel('Fitness Value')
plt.title('Convergence Rate of Fitness Values')
plt.legend()
plt.grid(True)
plt.show()

# Plot standard deviation and variance
plt.figure(figsize=(10, 6))
plt.plot(df['Generation'], df['Standard Deviation'], label='Standard Deviation')
plt.plot(df['Generation'], df['Variance'], label='Variance')

plt.xlabel('Generation')
plt.ylabel('Value')
plt.title('Standard Deviation and Variance of Fitness Values')
plt.legend()
plt.grid(True)
plt.show()

# Plot convergence rate
plt.figure(figsize=(10, 6))
plt.plot(df['Generation'], df['Convergence Rate'], label='Convergence Rate')

plt.xlabel('Generation')
plt.ylabel('Convergence Rate')
plt.title('Convergence Rate Over Generations')
plt.legend()
plt.grid(True)
plt.show()

# Plot fitness distribution (histograms)
df[['Best Fitness Values', 'Average Fitness Value', 'Worst Fitness Value']].plot(kind='hist', alpha=0.5, bins=30, figsize=(10, 6))
plt.title('Distribution of Fitness Values')
plt.xlabel('Fitness Value')
plt.ylabel('Frequency')
plt.grid(True)
plt.show()

# Rolling average (smooth the data)
rolling_window = 10
df['Rolling Best Fitness'] = df['Best Fitness Values'].rolling(window=rolling_window).mean()
df['Rolling Average Fitness'] = df['Average Fitness Value'].rolling(window=rolling_window).mean()
df['Rolling Worst Fitness'] = df['Worst Fitness Value'].rolling(window=rolling_window).mean()

plt.figure(figsize=(10, 6))
plt.plot(df['Generation'], df['Rolling Best Fitness'], label=f'Best Fitness (Rolling {rolling_window})')
plt.plot(df['Generation'], df['Rolling Average Fitness'], label=f'Average Fitness (Rolling {rolling_window})')
plt.plot(df['Generation'], df['Rolling Worst Fitness'], label=f'Worst Fitness (Rolling {rolling_window})')

plt.xlabel('Generation')
plt.ylabel('Fitness Value')
plt.title('Rolling Average of Fitness Values')
plt.legend()
plt.grid(True)
plt.show()
