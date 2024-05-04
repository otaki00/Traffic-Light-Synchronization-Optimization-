import pandas as pd
import matplotlib.pyplot as plt

# Load data
data = pd.read_csv('ga_performance.csv')
plt.figure(figsize=(10, 5))
plt.plot(data['Generation'], data['BestFitness'], label='Best Fitness')
plt.plot(data['Generation'], data['AverageFitness'], label='Average Fitness')
plt.plot(data['Generation'], data['WorstFitness'], label='Worst Fitness')
plt.xlabel('Generation')
plt.ylabel('Fitness')
plt.title('Convergence Analysis of GA')
plt.legend()
plt.grid(True)
plt.show()


# # T-test for BestFitness being significantly higher than 85
# t_stat, p_val = stats.ttest_1samp(data['BestFitness'], popmean=85)
# print(f"T-test Results: T-statistic = {t_stat}, P-value = {p_val}")

# # Linear Regression on BestFitness against Generation
# X = sm.add_constant(data['Generation'])  # Adds a constant term to the predictor
# y = data['BestFitness']
# model = sm.OLS(y, X)
# results = model.fit()
# print(results.summary())