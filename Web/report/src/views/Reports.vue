<template>
  <div class="reports-container">
    <h1>Reports</h1>
    <div v-for="(report, index) in reports" :key="index" class="report-card">
      <div class="report-info">
        <h2>{{ report.title }}</h2>
        <img :src="report.image" alt="Report Image" class="report-image">
        <p>{{ report.description }}</p>
        <p><strong>Urgency:</strong> {{ report.urgency }}</p>
      </div>
      <div class="report-status">
        <label for="status">Status:</label>
        <select v-model="report.status" @change="updateStatus(index, report.status)">
          <option value="not done">Not Done</option>
          <option value="in progress">In Progress</option>
          <option value="done">Done</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const reports = ref([
  {
    title: 'Report 1',
    image: 'src/assets/images/cat.jpg',
    description: 'Description for report 1',
    urgency: 'High',
    status: 'not done',
  },
  {
    title: 'Report 2',
    image: 'src/assets/images/cat.jpg',
    description: 'Description for report 2',
    urgency: 'Medium',
    status: 'in progress',
  },
  // Add more reports as needed
]);

const updateStatus = (index, status) => {
  reports.value[index].status = status;
  console.log(`Updated report ${index} to status: ${status}`);
};
</script>

<style scoped>
.reports-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  color: #333; /* Dark text color for better contrast */
}

.report-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  background-color: #fff; /* White background for better contrast */
}

.report-info {
  flex: 1;
  margin-right: 20px;
}

.report-image {
  max-width: 100px;
  border-radius: 8px;
}

.report-status {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.report-status label {
  margin-bottom: 8px;
  color: #333; /* Dark text color for better contrast */
}

.report-status select {
  padding: 5px;
  border-radius: 4px;
  border: 1px solid #ccc;
  background-color: #fff; /* White background for better contrast */
  color: #333; /* Dark text color for better contrast */
}
</style>
