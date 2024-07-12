import pandas as pd
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