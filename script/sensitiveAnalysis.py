import pandas as pd
import matplotlib.pyplot as plt
import glob
import os

# Load all log files
files_w1 = glob.glob('data/performance_w1_*.csv')
files_w2 = glob.glob('data/performance_w2_*.csv')
files_w3 = glob.glob('data/performance_w3_*.csv')

# Function to load and plot data
def load_and_plot(files, weight_name):
    plt.figure(figsize=(15, 10))
    for file in files:
        df = pd.read_csv(file)
        key = os.path.basename(file).replace('.csv', '')
        plt.plot(df['Generation'], df['BestFitness'], label=key)
    plt.xlabel('Generation')
    plt.ylabel('Best Fitness')
    plt.title(f'Sensitivity Analysis for {weight_name}')
    plt.grid(True)
    plt.legend(fontsize='small')
    plt.show()

# Plot grouped results
load_and_plot(files_w1, 'w1')
load_and_plot(files_w2, 'w2')
load_and_plot(files_w3, 'w3')
