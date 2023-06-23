# Document Search Engine

**A Web Application Designed To Facilitate Navigation Within The UW Campus**

## Introduction

A UW campus navigator that can find the shortest paths between the two locations selected by the users.

## Getting Started

### Prerequisites
-   Node.JS
-   npm
-   Gradle
-   Java
-   Git

### Installation
-   Clone the repo
```bash
git clone https://github.com/bohanw16/CampusNavigator.git
cd CampusNavigator
```
-   Install the dependencies
```bash
cd hw-campuspaths
npm install --no-audit
```

### Try it out
<!-- -   run gradle task runSpark in hw-campuspaths-server and run npm start in hw-campuspaths -->
-   Start the backend server to port:4567 with Gradle
    -   From $\texttt{hw-campuspaths-server}$, run gradle task $\texttt{runSpark}$
-   Launch the navigator to port:3000
    -   From $\texttt{hw-campuspaths}$, run
```bash
npm start
```
-   Explore the navigator at port:3000
    -   Example when using a local machine:  http://localhost:3000/
    -   Example when using a virtual machine:  http://hostname.domain:3000/

### Clean up
-   If you want to clean up the compiled files, from $\texttt{hw-campuspaths}$ run
```bash
npm uninstall
```
