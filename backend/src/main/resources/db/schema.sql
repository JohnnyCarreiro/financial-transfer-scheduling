CREATE TABLE transfer_fees (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  min_days INT NOT NULL,
  max_days INT,
  fixed_fee DECIMAL(10, 2) NOT NULL,
  percentage_fee DECIMAL(5, 3) NOT NULL
);

CREATE TABLE transfers (
  id CHAR(36) PRIMARY KEY,
  source_account VARCHAR(255) NOT NULL,
  destination_account VARCHAR(255) NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  scheduled_date TIMESTAMP NOT NULL,
  transfer_date TIMESTAMP,  
  transfer_fee_id CHAR(36) NOT NULL,
  status VARCHAR(20) NOT NULL CHECK (status IN ('scheduled', 'completed', 'cancelled', 'failed')),  
  FOREIGN KEY (transfer_fee_id) REFERENCES transfer_fees(id)
);
