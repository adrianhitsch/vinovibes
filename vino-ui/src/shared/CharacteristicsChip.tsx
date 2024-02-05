import React from 'react';

const CharacteristicsChip = (item: string, data: Array<string>) => {
  console.log(item);
  console.log(data);

  if (data.findIndex((element) => element === item) === data.length - 1) {
    return (
      <div className="characteristicsChip">
        <span className="icon icon-plus"></span>
      </div>
    );
  }
  return (
    <div className="characteristicsChip">
      <span>{item}</span>
    </div>
  );
};

export default CharacteristicsChip;
