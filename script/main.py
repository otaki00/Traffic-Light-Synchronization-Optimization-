import pandas as pd
import matplotlib.pyplot as plt
import glob  # To read all files matching a pattern

# Find all CSV files that match the pattern
files = glob.glob('data/island_*.csv')  # Adjust the pattern based on your file naming convention


def visualize_last_fitness_genes(file_path):
    # Load the CSV file
    data = pd.read_csv(file_path)

    # Select the last row
    last_entry = data.iloc[-1]

    # Gene names based on your CSV column names
    gene_names = ['GR Timing Set1', 'GR Timing Set2', 'GR Timing Set3', 'Cycle Length', 'Offset']

    # Extract gene values from the last row
    gene_values = [last_entry[name] for name in gene_names]

    # Plotting the gene data
    plt.figure(figsize=(10, 5))
    plt.bar(gene_names, gene_values, color='royalblue')
    plt.xlabel('Gene Type')
    plt.ylabel('Value')
    plt.title('Gene Values for the Last Recorded Fitness Function')
    plt.xticks(rotation=45)  # Rotate labels to fit longer names
    plt.tight_layout()  # Adjust layout to not cut off labels
    plt.show()

# Usage example - replace 'yourfile.csv' with the actual path to your CSV file


# Load and combine all data
all_data = []
for filename in files:
    visualize_last_fitness_genes(filename)
    df = pd.read_csv(filename)
    df['Island'] = "Island "+filename.split('_')[-1].split('.')[0]  
    all_data.append(df)

combined_data = pd.concat(all_data)

# Plotting
plt.figure(figsize=(12, 6))

# Group data by the island and plot
for key, group in combined_data.groupby('Island'):
    plt.plot(group['Generation'], group['BestFitness'], label=f'Best Fitness - {key}')

plt.xlabel('Generation')
plt.ylabel('Fitness')
plt.title('Convergence Analysis of GA Across Islands')
plt.legend()
plt.grid(True)
plt.show()
