import pandas as pd
import matplotlib.pyplot as plt

# Load the results CSV file
file_path = 'data/seq/results.csv'  # Adjust the path if needed
data = pd.read_csv(file_path)

# Convert time from milliseconds to seconds
data['TimeTaken(s)'] = data['TimeTaken(ms)'] / 1000

# Visualize the results
plt.figure(figsize=(10, 6))
plt.plot(data['Node'], data['TimeTaken(s)'], marker='o', linestyle='-', color='b')
plt.xlabel('Number of Nodes')
plt.ylabel('Time Taken (s)')
plt.title('Time Taken for Different Numbers of Nodes')
plt.grid(True)
plt.show()
