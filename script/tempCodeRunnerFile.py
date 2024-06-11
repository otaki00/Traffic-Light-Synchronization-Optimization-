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

# Load and combine all data
all_data = []
for filename in files:
    # visualize_last_fitness_genes(filename)
    df = pd.read_csv(filename)
    df['Island'] = "Island " + filename.split('_')[-1].split('.')[0]
    all_data.append(df)

combined_data = pd.concat(all_data)

# Plotting fitness progression for each island
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

# Plotting histograms for each gene value
fig, axs = plt.subplots(3, 2, figsize=(15, 15))

# GR Timing Set1
axs[0, 0].hist(combined_data['GR Timing Set1'], bins=20, color='skyblue', edgecolor='black')
axs[0, 0].set_title('GR Timing Set1')
axs[0, 0].set_xlabel('Value')
axs[0, 0].set_ylabel('Frequency')

# GR Timing Set2
axs[0, 1].hist(combined_data['GR Timing Set2'], bins=20, color='lightgreen', edgecolor='black')
axs[0, 1].set_title('GR Timing Set2')
axs[0, 1].set_xlabel('Value')
axs[0, 1].set_ylabel('Frequency')

# GR Timing Set3
axs[1, 0].hist(combined_data['GR Timing Set3'], bins=20, color='lightcoral', edgecolor='black')
axs[1, 0].set_title('GR Timing Set3')
axs[1, 0].set_xlabel('Value')
axs[1, 0].set_ylabel('Frequency')

# Cycle Length
axs[1, 1].hist(combined_data['Cycle Length'], bins=20, color='lightsalmon', edgecolor='black')
axs[1, 1].set_title('Cycle Length')
axs[1, 1].set_xlabel('Value')
axs[1, 1].set_ylabel('Frequency')

# Offset
axs[2, 0].hist(combined_data['Offset'], bins=20, color='lightseagreen', edgecolor='black')
axs[2, 0].set_title('Offset')
axs[2, 0].set_xlabel('Value')
axs[2, 0].set_ylabel('Frequency')

# Remove empty subplot
fig.delaxes(axs[2, 1])

plt.tight_layout()
plt.show()

# Calculating the correlation matrix
correlation_matrix = combined_data[['BestFitness', 'GR Timing Set1', 'GR Timing Set2', 'GR Timing Set3', 'Cycle Length', 'Offset']].corr()

# Plotting the correlation matrix
plt.figure(figsize=(10, 8))
plt.matshow(correlation_matrix, fignum=1, cmap='coolwarm')
plt.colorbar()
plt.xticks(range(len(correlation_matrix.columns)), correlation_matrix.columns, rotation=45, ha='left')
plt.yticks(range(len(correlation_matrix.index)), correlation_matrix.index)
plt.title('Correlation Matrix of Fitness and Gene Values', pad=20)
plt.show()

# Visualize the last gene values for each island
for filename in files:
    visualize_last_fitness_genes(filename)
