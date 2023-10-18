export function formatDateTime(dateString: string) {
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    hour12: true,
  };

  const formattedDate = new Intl.DateTimeFormat('en-US', options).format(new Date(dateString));
  return formattedDate.replace(/(\d+)\/(\d+)\/(\d+), (\d+):(\d+) (AM|PM)/, '$3.$1.$2 오후 $4:$5');
}

export function formatDate(dateString: string) {
  const options: Intl.DateTimeFormatOptions = {
    year: "numeric",
    month: "short",
    day: "numeric"
  };

  return new Intl.DateTimeFormat('ko-kr', options).format(new Date(dateString))
}

export function getWeekRange(date: Date): { text: string, param: string, weekdays: string[] } {
  const dayOfWeek = date.getDay() // 주어진 날짜의 요일 (0: 일요일, 1: 월요일, ..., 6: 토요일)
  const startDate = new Date(date) // 입력된 날짜를 복사하여 시작 날짜로 설정
  const endDate = new Date(date)

  // 입력된 날짜가 일요일이면 startDate와 endDate를 변경하지 않고, 그대로 사용
  // 그 외의 경우, 입력된 날짜를 일요일로 맞춤
  startDate.setDate(date.getDate() - dayOfWeek) // 현재 날짜에서 요일을 뺌으로써 일요일을 구함
  endDate.setDate(date.getDate() - dayOfWeek + 6) // 일요일에서 6일을 더하여 토요일을 구함

  const startYear = startDate.getFullYear()
  const startMonth = startDate.getMonth() + 1
  const startDay = startDate.getDate()
  const endYear = endDate.getFullYear()
  const endMonth = endDate.getMonth() + 1
  const endDay = endDate.getDate()

  const weekdays = [1, 2, 3, 4, 5].map((day) => {
    const date = new Date()
    date.setDate(startDate.getDate() + day)
    return `${date.getMonth() + 1}/${date.getDate()}`
  })

  return {
    text: `${startYear}년 ${startMonth}월 ${startDay}일 ~ ${endYear}년 ${endMonth}월 ${endDay}일`,
    param: `${startYear}${startMonth}${startDay}${endYear}${endMonth}${endDay}`,
    weekdays: weekdays
  }
}
